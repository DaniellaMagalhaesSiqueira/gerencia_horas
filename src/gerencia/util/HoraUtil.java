package gerencia.util;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoraUtil {

	public static Map<Integer, Duration> pegarHorasDiasUteis(String tabela, String entrada, String saida) {
		Map<Integer, Duration> horasPorDia = new HashMap<Integer, Duration>();
		// Depurando valores

		List<String> blocos = new ArrayList<String>();
		for (String str : tabela.split("\n")) {
			blocos.add(str);
		}
		String separador = "";
		if (blocos.get(0).substring(2, 3).equals(" "))
			separador = " ";
		if (blocos.get(0).substring(2, 3).equals("\t"))
			separador = "\t";
		for (String bloco : blocos) {
			String[] dia = bloco.split(separador);
			for (String d : dia) {
				System.out.println("D->" + d);
			}
			if (!bloco.isEmpty()) {
				for (int i =0; i < dia.length; i++) {
					dia[i] = dia[i].replace("\n", "").replace("\r", "").replaceAll("\t", "");
					dia[i].trim();
					if (dia[i].isEmpty()) {
						dia[i] = null;
					}
				}
				Integer data;
				try {
					data = Integer.parseInt(dia[0]);
					boolean entr = false;
					boolean sai = false;
					if (DataUtil.saberDiaUtil(dia[1])) {
						if (dia.length >= 3 && dia.length <= 4) {
							LocalTime lt = converter(dia[2]);
							// Se a hora sozinha for menor que 10 a hora padrão é entrada
							if (lt.getHour() < 10) {
								sai = true;
							}
							if (lt.getHour() > 12) {
								entr = true;
							}
						}
					}
					Duration d = Duration.ofMinutes(0);
					if (dia.length >= 3) {

						if (dia[2] != null) {
							LocalTime lt = converter(dia[2]);// hora 1
							LocalTime lt2 = lt;
							if ((entr && entrada == null) || (sai && saida == null)) {
								lt = LocalTime.of(0, 0);
								lt2 = LocalTime.of(0, 0);
							} else if (entr && entrada != null) {
								lt = converter(entrada);
								lt2 = converter(dia[2]);
							} else if (sai && saida != null) {
								lt2 = converter(saida);
							} else if (dia[3] != null) {
								lt2 = converter(dia[3]);// hora 2
							}
							d = Duration.between(lt, lt2);
							horasPorDia.put(data, d.toHours() > 7 ? d.minusHours(1) : d);
						}
					}
				} catch (NumberFormatException e) {
					horasPorDia.clear();
					e.printStackTrace();
					return horasPorDia;
				}
			}
		}

		return horasPorDia;
	}


	public static Duration horasTotais(Map<Integer, Duration> horas) {
		Duration result = Duration.ofHours(0);

		for (Duration val : horas.values()) {
			result = result.plus(val);
		}

		return result;
	}

	public static String horasExtras(Map<Integer, Duration> horas, int cargaHorariaSemanal) {

		Duration result = Duration.ofHours(0);
		for (Duration val : horas.values()) {
			result = result.plus(val);
		}
		int horasNecessarias = cargaHorariaSemanal * 4;
		Duration hn = Duration.ofHours(horasNecessarias);
		result = result.minus(hn);
		if (result.isNegative()) {
			return "Devendo Horas: " + result.toString();
		}

		return result.toString();

	}

	public static boolean verificarNumerico(String str) {
		boolean result = true;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (!Character.isDigit(c[i])) {
				result = false;
			}
		}

		return result;
	}

	public static int[] convert(String valor) {
		try {
			int[] result = { Integer.parseInt(valor.substring(0, 2)), Integer.parseInt(valor.substring(3, 5)) };
			return result;
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static Duration converterDuration(String valor) {
		Duration duration = Duration.ofHours(Integer.parseInt(valor.substring(0, 2)));
		duration = duration.plusMinutes(Integer.parseInt(valor.substring(3, 5)));
		return duration;
	}

	public static LocalTime converter(String val) {
		if (val.isEmpty() || val.trim().equals("")) {
			return LocalTime.of(0, 0);
		}
		int[] valor = convert(val);
		LocalTime lt = LocalTime.of(valor[0], valor[1]);

		return lt;
	}

	public static int horasDevidas(int cargaHora, int mes, int ano, int feriados) {
		int diasUteis = DataUtil.diasUteis(mes, ano) - feriados;
		int horasDiarias = 0;
		if (cargaHora == 40) {
			horasDiarias = 8;
		}
		if (cargaHora == 20) {
			horasDiarias = 4;
		}

		int horasDevidas = horasDiarias * diasUteis;
		return horasDevidas;

	}
	public static int horasDevidas(int cargaHora, String tabela, int feriados) {
		int diasUteis = DataUtil.diasUteis(tabela) - feriados;
		int horasDiarias = 0;
		if (cargaHora == 40) {
			horasDiarias = 8;
		}
		if (cargaHora == 20) {
			horasDiarias = 4;
		}

		int horasDevidas = horasDiarias * diasUteis;
		return horasDevidas;
	}

}
